# install bottle to run the program -> pip install bottle
# run the program by calling python backend.py and then go to the url
# 127.0.0.1:8080/index on your browser
import json, sqlite3

# create databases in memory itself
conn = sqlite3.connect(':memory:')

# c is the cursor where you run queries
c = conn.cursor()
c.execute('''CREATE TABLE login_doc (uname varchar primary key, email varchar, pwd varchar, dep varchar)''')
c.execute('''CREATE TABLE login_pat (uname varchar primary key, age varchar, email varchar, pwd varchar)''')
c.execute('''CREATE TABLE appointments (uname_doc, uname_pat, dt varchar, time varchar, FOREIGN KEY(uname_doc) REFERENCES login_doc(uname), FOREIGN KEY(uname_pat) REFERENCES login_pat(uname))''')
conn.commit()

from bottle import request, template, route, run, static_file

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
    # this is a very shitty way of doing things because of SQL injections but fuck it
    x = c.execute('SELECT * FROM login_doc WHERE email="%s" AND pwd = "%s"' % (emailid, password)).fetchone()
    if x:
        # apts has the list of appointments for the particular
        apts = c.execute('SELECT * FROM appointments WHERE uname_doc="%s"' % x[0]).fetchall()
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
    c.execute('INSERT INTO login_doc(uname, email, pwd, dep) VALUES ("%s", "%s", "%s", "%s")' % (username, mailid, password, specialization))
    conn.commit()

    return template(
           'doctor_app',
            doc_name = username,
            doctor_appointment = {})

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

# @route('/<filename>')
# def ret_file(filename):
#     return static_file(filename, root='')

# change localhost to your systems ip address, which will enable you
# to run the app from other pc's, with your pc being the server and the
# other pc being the client
run(host='localhost', port=5000)
