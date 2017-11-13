from bottle import route, run, request, template
import sqlite3

con = sqlite3.connect('ehealth_data.db')
c = con.cursor()
c.execute('''CREATE TABLE IF NOT EXISTS patient_details (uname varchar PRIMARY KEY NOT NULL, emailid varchar NOT NULL, password varchar NOT NULL, age varchar NOT NULL)''')
c.execute('''CREATE TABLE IF NOT EXISTS doctor_details (uname varchar PRIMARY KEY NOT NULL, emailid varchar NOT NULL, password varchar NOT NULL, spec varchar NOT NULL)''')
c.execute('''CREATE TABLE IF NOT EXISTS appointments (uname_doc, uname_patient, dt varchar NOT NULL, ap_time varchar NOT NULL, FOREIGN KEY(uname_doc) REFERENCES doctor_details(uname), FOREIGN KEY(uname_patient) REFERENCES patient_details(uname))''')
con.commit()

@route('/index')
def show_index():
    return template('login_page')

@route('/signup_doc')
def show_signup_page():
    return template('doctor_signup')

@route('/signup_doc', method='post')
def take_signup_data():
    doc_name = request.forms.get('doc_name')
    emailid = request.forms.get('emailid')
    password = request.forms.get('password')
    spec = request.forms.get('spec')
    print doc_name, emailid, password, spec
    c.execute('''INSERT INTO doctor_details(uname, emailid, password, spec) VALUES ("%s", "%s", "%s", "%s")''' % (doc_name, emailid, password, spec))
    con.commit()
    return template('doctor_loggedin', doc_name=doc_name)

@route('/signup_pat')
def show_signup_page():
    return template('patient_signup')

@route('/signup_pat', method='post')
def take_signup_data():
    pat_name = request.forms.get('pat_name')
    emailid = request.forms.get('emailid')
    password = request.forms.get('password')
    age = request.forms.get('age')
    print pat_name, emailid, password, age
    c.execute('''INSERT INTO patient_details(uname, emailid, password, age) VALUES ("%s", "%s", "%s", "%s")''' % (pat_name, emailid, password, age))
    con.commit()
    return template('patient_loggedin', pat_name=pat_name, apts=[])

@route('/add_appointment/<pat_name>')
def add_appointments(pat_name):
    doc_list = c.execute('''SELECT uname, spec FROM doctor_details''').fetchall()
    print doc_list
    return template('add_appointment', doc_list=doc_list, pat_name=pat_name)

@route('/add_appointment/<pat_name>', method='post')
def insert_appointment_in_table(pat_name):
    doc_name = request.forms.get('doctor_selected')
    date_time = request.forms.get('datepicker')
    date, time = date_time.split('T')[0], date_time.split('T')[1]
    c.execute('''INSERT INTO appointments(uname_doc, uname_patient, dt, ap_time) VALUES ("%s", "%s", "%s", "%s")'''%(doc_name, pat_name, date, time))
    con.commit()
    apts = c.execute('''SELECT * FROM appointments WHERE uname_patient like "%s"'''%(pat_name)).fetchall()
    print doc_name, date_time
    return template('patient_loggedin', pat_name=pat_name, apts=apts)

@route('/login', method='post')
def check_which_type_of_login():
    emailid = request.forms.get('emailid')
    password = request.forms.get('password')
    x = c.execute('''SELECT * FROM patient_details WHERE emailid like "%s" and password like "%s"'''%(emailid, password)).fetchone()
    y = c.execute('''SELECT * FROM doctor_details WHERE emailid like "%s" and password like "%s"'''%(emailid, password)).fetchone()
    if x:  # patient hain
        apts = c.execute('''SELECT * FROM appointments WHERE uname_patient like "%s"'''%(x[0])).fetchall()
        return template('patient_loggedin', pat_name=x[0], apts=apts)
    elif y:
        apts = c.execute('''SELECT * FROM appointments WHERE uname_doc like "%s"'''%(y[0])).fetchall()
        print apts
        return template('doctor_loggedin', doc_name=y[0], apts=apts)
    else:
        return '''<h1>Invalid credentials</h1>
                  <a href="/index">Go to login page</a>'''


run(host='localhost', port=8080)
