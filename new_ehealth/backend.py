# install bottle to run the program -> pip install bottle
# run the program by calling python backend.py and then go to the url
# 127.0.0.1:8080/index on your browser
import json, psycopg2
import os
import requests

# for bottle to work correctly on Windows - determine the absolute path
abs_app_dir_path = os.path.dirname(os.path.realpath(__file__))
abs_views_path = os.path.join(abs_app_dir_path, 'views')

# no fancy checks, since we will be moving to a Spring based solution
DB_USERNAME = os.environ.get('DB_USERNAME', '')
DB_PASSWORD = os.environ.get('DB_PASSWORD', '')
DB_HOST = os.environ.get('DB_HOST', '192.168.99.101')
# the DB port uses the kubernetes node port default
DB_PORT = os.environ.get('DB_PORT', 32432)
DB_NAME = os.environ.get('DB_NAME', 'ehealth')
SPRING_BACKEND_ENDPOINT = os.environ.get('SPRING_BACKEND_ENDPOINT', 'http://localhost:8080')

if (DB_USERNAME == '' or DB_PASSWORD == ''):
    print('Database username / password not defined. Aborting startup...')
    exit(1)

conn = psycopg2.connect(user = DB_USERNAME,
                        password = DB_PASSWORD,
                        host = DB_HOST,
                        port = DB_PORT,
                        database = DB_NAME)

# c is the cursor where you run queries
c = conn.cursor()

from bottle import request, template, route, run, static_file

import bottle

bottle.TEMPLATE_PATH.insert(0, abs_views_path)

# refer the odd even sort program for a simple explanation of routes
# this route tells us what to do when the user enters the index page
@route('/index')
def index_page():  # set this function name to any name of your choice
# return the ehealth.html page
    return template('ehealth')

# if the user submits login information, we get here to the post method
# version of our index page
@route('/index', method='post')
def login_page():  # set this function name to any name of your choice
    # get the emailid and password entered by the user from the form
    emailid = request.forms.get('emailid')
    password = request.forms.get('password')
    # if the emailid and password match
    # if the user trying to login is a doctor
    print('Cursor is : ' + str(c))
    x = c.execute("SELECT * FROM login_doc WHERE email='{}' AND pwd='{}';".format(emailid, password)).fetchone()
    if x:
        # apts has the list of appointments for the particular
        apts = c.execute("SELECT * FROM appointments WHERE uname_doc='%s'" % x[0]).fetchall()
        # doc_name and doctor_appointment are the name of the
        # variable used in the doctor_app.html page
        return template(
           'doctor_app',
            doc_name = x[0],
            doctor_appointment = apts)

    x = c.execute('SELECT * FROM login_pat WHERE email="%s" AND pwd="%s"' % (emailid, password)).fetchone()
    if x:  # if the user trying to login was a patient
        apts = c.execute('SELECT * FROM appointments WHERE uname_pat="%s"' % x[0]).fetchall()
        return template(
           'patient_app',
            patient_name=x[0],
            patient_appointment=apts)

    return '''<h1>Credentials don't match</h1>
               <h2>New users should signup</h2>
               <a href="/index">Back to Home</a>'''

@route('/signup_d')
def signup_page():
    return template('signup_doctor')

@route('/signup_d', method='post')
def signup_page():
    username = request.forms.get('userid')
    password = request.forms.get('password')
    specialization = request.forms.get('user_job')
    mailid = request.forms.get('mailid')

    backend_response = requests.post(SPRING_BACKEND_ENDPOINT + '/signup_d',
                                    data = json.dumps({
                                        'username': username,
                                        'password': password,
                                        'specialization': specialization,
                                        'mailid': mailid
                                    }),
                                    headers = {'Content-Type': 'application/json'})
    if backend_response.status_code in (200, 201):
        return template(
               'doctor_app',
                doc_name = username,
                doctor_appointment = {})
    else:
        return '<h1>Error pulling up doctor information!</h1><b><a href="/index">Back home</a>'

@route('/signup_p')
def signup_page():
    return template('signup_patient')

@route('/signup_p', method='post')
def signup_page():
    username = request.forms.get('userid')
    password = request.forms.get('password')
    age = request.forms.get('age')
    mailid = request.forms.get('mailid')
    c.execute('INSERT INTO login_pat(uname, age, email, pwd) VALUES ("%s", "%s", "%s", "%s")' % (username, age, mailid, password))
    return template(
           'patient_app',
            patient_name = username,
            patient_appointment = {})

@route('/add_appoint/<appointee>')
def appointment_page(appointee):
    temp = []
    docs = c.execute('SELECT * FROM login_doc').fetchall()
    backend_response = requests.post(SPRING_BACKEND_ENDPOINT + '/doctor')
    if backend_response.status_code in (200, 201):
        return template(
               'doctor_app',
                doc_name = username,
                doctor_appointment = {})
    else:
        return '<h1>Error pulling up doctor information!</h1><b><a href="/index">Back home</a>'
    for i in docs:
        temp1 = '_'.join(i[0].split(' '))
        temp2 = i[3]
        temp.append((temp1, temp2))
    # return the list of all doctors with their specializations
    return template(
           'add_app',
            appointee = appointee,
            doc_names_spec = temp)

@route('/add_appoint/<appointee>', method='post')
def appointment_page(appointee):
    selected_button = request.forms['radiobutton']
    selected_button = ' '.join(selected_button.split('_'))
    temp_date = request.forms.get('datepicker')
    c.execute('INSERT INTO appointments VALUES("%s", "%s", "%s", "%s")' % (selected_button, appointee, temp_date.split('T')[0], temp_date.split('T')[1]))
    # always commit after inserts
    conn.commit()

    x = c.execute('SELECT * FROM appointments WHERE uname_pat = "%s"' % appointee).fetchall()

    return template('patient_app', patient_name=appointee, patient_appointment=x)

run(host='localhost', port=5000, debug=True)