# install bottle to run the program -> pip install bottle
# run the program by calling python backend.py and then go to the url
# 127.0.0.1:8080/index on your browser
import json
from bottle import request, template, route, run, static_file
# open our database and load the values in them in variables
# IN THE EXAM YOU HAVE TO CREATE THE JSON FILES
# ALL EMPTY JSON FILES SHOULD STORE {} if they are empty
# ADD A DOCTOR BEFORE ADDING PATIENTS! (OR SUFFER)
# doc_app.json stores the doctors appointments in json format
# patient_app.json stores the patients appointments in json format
# user_pass stores the username password combination in json format
# details stores the
with open('doc_app.json') as fp1, open('patient_app.json') as fp2:
    doctors_appointments = json.load(fp1)
    patients_appointments = json.load(fp2)
with open('user_pass.json') as fp3, open('details.json') as fp4:
    user_pass = json.load(fp3)
    details = json.load(fp4)

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
    # if the emailid is valid and the password matches
    if emailid in user_pass and password == user_pass[emailid]:
        # if the user trying to login is a doctor
        if details[emailid]['type'] == 'doctor':
            try:
                # apts has the list of appointments for the particular
                # doctor
                apts = doctors_appointments[details[emailid]['name']]
                # doc_name and doctor_appointment are the name of the
                # variable used in the doctor_app.html page
                return template(
                       'doctor_app',
                        doc_name = details[emailid]['name'],
                        doctor_appointment = apts)
            except:
            # if the list of appointments for a doctor don't exist
            # generate this list and store the records in the
            # doc_app.json file
                # name has the name of the doctor
                name = details[emailid]['name']
                temp = {}
                temp[name] = []
                # eg. for doctor suresh -> temp = {suresh:[]}
                for patient in patients_appointments:
                # patients_appointments is the list containing the list
                # of appointments for a patient. we got this from the
                # patient_app.json file at the start of our program
                    for appointment in patients_appointments[patient]:
                        # if the name of the doctor matches the name
                        # on the appointment
                        if name == appointment['doctor']:
                            temp[name].append(
                                {'date': appointment['date'],
                                 'time': appointment['time'],
                                 'patient': patient})
                # store all the doctors appointments under his name
                doctors_appointments[name] = temp[name]
                # make a copy of the variable doctors_appointments in
                # the doc_app.json file
                with open('doc_app.json', 'w') as fp1:
                    json.dump(doctors_appointments, fp1)
                # doc_name and doctor_appointment are the name of the
                # variable used in the doctor_app.html page
                return template(
                       'doctor_app',
                        doc_name=details[emailid]['name'],
                        doctor_appointment=doctors_appointments[name])
        else:  # if the user trying to login was a patient
            try:
                apts = patients_appointments[details[emailid]['name']]
                return template(
                       'patient_app',
                        patient_name = details[emailid]['name'],
                        patient_appointment = apts)
            except:
                return template(
                       'patient_app',
                        patient_name=details[emailid]['name'],
                        patient_appointment={})
    else:  # if the username doesn't exist
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
    user_pass[mailid] = password
    details[mailid] = {'name': username, 'specialization': specialization, 'type': 'doctor'}
    # store the details obtained from the signup page in the json files
    with open('details.json', 'w') as fp1:
        json.dump(details, fp1)
    with open('user_pass.json', 'w') as fp2:
        json.dump(user_pass, fp2)
    doctors_appointments[mailid] = {}
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
    user_pass[mailid] = password
    details[mailid] = {'name': username, 'age': age, 'type': 'patient'}
    # store the details obtained from the signup page in the json files
    with open('details.json', 'w') as fp1:
    # store the details of the user -> name, age, type
        json.dump(details, fp1)
    with open('user_pass.json', 'w') as fp2:
    # store the username, password combination
        json.dump(user_pass, fp2)
    patients_appointments[mailid] = {}   # no appointments intially
    return template(
           'patient_app',
            patient_name = username,
            patient_appointment = {})

@route('/add_appoint/<appointee>')
def appointment_page(appointee):
    temp = []
    for email in details:
        if details[email]['type']  == 'doctor':
            temp1 = '_'.join(details[email]['name'].split(' '))
            temp2 = details[email]['specialization']
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
    print selected_button
    temp_date = request.forms.get('datepicker')
    print temp_date
    try:
        patients_appointments[appointee].append({'date': temp_date.split('T')[0], 'time': temp_date.split('T')[1], 'doctor': selected_button})
    except:
        patients_appointments[appointee] = []
        patients_appointments[appointee].append({'date': temp_date.split('T')[0], 'time': temp_date.split('T')[1], 'doctor': selected_button})
    with open('patient_app.json', 'w') as fp1:
        json.dump(patients_appointments, fp1)
    return template('patient_app', patient_name=appointee, patient_appointment=patients_appointments[appointee])

@route('/<filename>')
def ret_file(filename):
    return static_file(filename, root='')

# change localhost to your systems ip address, which will enable you
# to run the app from other pc's, with your pc being the server and the
# other pc being the client
run(host='localhost', port=8080)
