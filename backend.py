import bottle, json
from bottle import request, template, route, run, static_file
# read the doctor file
with open('doc_app.json') as fp1, open('patient_app.json') as fp2, open('user_pass.json') as fp3, open('details.json') as fp4:
    doctors_appointments = json.load(fp1)
    patients_appointments = json.load(fp2)
    user_pass = json.load(fp3)
    details = json.load(fp4)  # stores the user details as name, type, specialization

# maybe it has a higher priority
@route('/index')
def index_page():
    return template('ehealth')

@route('/index', method='post')
def login_page():
    # add a feature if database empty!
    emailid = request.forms.get('emailid')
    password = request.forms.get('password')
    if emailid in user_pass and password == user_pass[emailid]:
        if details[emailid]['type'] == 'doctor':
            try:
                return template('doctor_app', doc_name=details[emailid]['name'], doctor_appointment=doctors_appointments[details[emailid]['name']])
            except:
                 name = details[emailid]['name']
                 temp = {}
                 temp[name] = []
                 for patient in patients_appointments:
                     for appointment in patients_appointments[patient]:
                         if name == appointment['doctor']:
                             temp[name].append({'date': appointment['date'], 'time': appointment['time'], 'patient': patient})
                 doctors_appointments[name] = temp[name]
                 with open('doc_app.json', 'w') as fp1:
                     json.dump(doctors_appointments, fp1)
                 return template('doctor_app', doc_name=details[emailid]['name'], doctor_appointment=doctors_appointments[name])
        else:  # if a patient
            try:
                return template('patient_app', patient_name=details[emailid]['name'], patient_appointment=patients_appointments[details[emailid]['name']])
            except:
                return template('patient_app', patient_name=details[emailid]['name'], patient_appointment={})
    else:
        return '''<h1>Credentials don't match</h1>
                  <a href="/index">Back to login</a>'''

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
    with open('details.json', 'w') as fp1, open('user_pass.json', 'w') as fp2:
        json.dump(details, fp1)
        json.dump(user_pass, fp2)
    doctors_appointments[mailid] = {}
    return template('doctor_app', doc_name=username, doctor_appointment={})

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
    with open('details.json', 'w') as fp1, open('user_pass.json', 'w') as fp2:
        json.dump(details, fp1)
        json.dump(user_pass, fp2)
    patients_appointments[mailid] = {}
    return template('patient_app', patient_name=username, patient_appointment={})

@route('/add_appoint/<appointee>')
def appointment_page(appointee):
    return template('add_app', appointee=appointee, doc_names_spec=[('_'.join(details[email]['name'].split(' ')), details[email]['specialization']) for email in details if details[email]['type'] == 'doctor'])

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

run(host='localhost', port=8080)
