let authHeader = '';
let currentRole = '';

function registerPatient() {
    const data = {
        username: document.getElementById('regUsername').value,
        password: document.getElementById('regPassword').value,
        name: document.getElementById('regName').value,
        age: parseInt(document.getElementById('regAge').value),
        contactNumber: document.getElementById('regContact').value
    };
    fetch('/api/auth/register', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
    .then(r => r.ok ? alert('Registered!') : r.text().then(t => alert(t)))
    .catch(err => alert(err));
}

function loginPatient() {
    const u = document.getElementById('patUser').value;
    const p = document.getElementById('patPass').value;
    authHeader = 'Basic ' + btoa(u + ':' + p);
    currentRole = 'PATIENT';
    document.getElementById('patient-area').style.display = 'block';
    document.getElementById('doctor-area').style.display = 'none';
    loadDoctors();
}

function loginDoctor() {
    const u = document.getElementById('docUser').value;
    const p = document.getElementById('docPass').value;
    authHeader = 'Basic ' + btoa(u + ':' + p);
    currentRole = 'DOCTOR';
    document.getElementById('doctor-area').style.display = 'block';
    document.getElementById('patient-area').style.display = 'none';
    loadDoctorAppointments();
}

function loadDoctors() {
    fetch('/api/doctor', {headers: {Authorization: authHeader}})
        .then(r => r.json())
        .then(list => {
            const sel = document.getElementById('doctorId');
            sel.innerHTML = '';
            list.forEach(d => {
                const opt = document.createElement('option');
                opt.value = d.id;
                opt.textContent = d.name + ' (' + d.specialty + ')';
                sel.appendChild(opt);
            });
        });
}

function createAppointment() {
    const data = {
        doctorId: parseInt(document.getElementById('doctorId').value),
        appointmentTime: document.getElementById('apptTime').value
    };
    fetch('/api/patient/appointments', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': authHeader
        },
        body: JSON.stringify(data)
    }).then(r => r.json())
      .then(appt => alert('Created appointment ' + appt.id))
      .catch(err => alert(err));
}

function loadPatientAppointments() {
    fetch('/api/patient/appointments', {headers: {Authorization: authHeader}})
        .then(r => r.json())
        .then(data => {
            document.getElementById('patientAppts').textContent = JSON.stringify(data, null, 2);
        });
}

function loadDoctorAppointments() {
    fetch('/api/doctor/appointments', {headers: {Authorization: authHeader}})
        .then(r => r.json())
        .then(list => {
            const div = document.getElementById('doctorAppts');
            div.innerHTML = '';
            list.forEach(ap => {
                const container = document.createElement('div');
                container.textContent = JSON.stringify(ap);
                const sel = document.createElement('select');
                ['PENDING','APPROVED','REJECTED'].forEach(s => {
                    const o = document.createElement('option');
                    o.value = s; o.textContent = s; if(ap.status===s) o.selected=true; sel.appendChild(o);
                });
                const notes = document.createElement('input');
                notes.placeholder = 'Notes';
                const btn = document.createElement('button');
                btn.textContent = 'Update';
                btn.onclick = () => updateAppointment(ap.id, sel.value, notes.value);
                container.appendChild(document.createElement('br'));
                container.appendChild(sel);
                container.appendChild(notes);
                container.appendChild(btn);
                div.appendChild(container);
            });
        });
}

function updateAppointment(id, status, notes) {
    fetch('/api/doctor/appointments/' + id + '/status', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': authHeader
        },
        body: JSON.stringify({status: status, notes: notes})
    }).then(() => loadDoctorAppointments());
}
