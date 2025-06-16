const { useState, useEffect } = React;

function App() {
  const [authHeader, setAuthHeader] = useState('');
  const [role, setRole] = useState('');
  const [doctors, setDoctors] = useState([]);
  const [doctorId, setDoctorId] = useState('');
  const [apptTime, setApptTime] = useState('');
  const [patientAppts, setPatientAppts] = useState([]);
  const [doctorAppts, setDoctorAppts] = useState([]);
  const [showLogin, setShowLogin] = useState(false);
  const [showRegister, setShowRegister] = useState(false);
  const [loginUser, setLoginUser] = useState('');
  const [loginPass, setLoginPass] = useState('');

  const registerPatient = async (e) => {
    e.preventDefault();
    const data = {
      username: e.target.regUsername.value,
      password: e.target.regPassword.value,
      name: e.target.regName.value,
      age: parseInt(e.target.regAge.value),
      contactNumber: e.target.regContact.value
    };
    const res = await fetch('/api/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    if (res.ok) {
      alert('Registered!');
      setShowRegister(false);
    } else {
      alert(await res.text());
    }
  };

  const loginPatient = (e) => {
    e.preventDefault();
    setAuthHeader('Basic ' + btoa(loginUser + ':' + loginPass));
    setRole('PATIENT');
    setShowLogin(false);
  };

  const loginDoctor = (e) => {
    e.preventDefault();
    setAuthHeader('Basic ' + btoa(loginUser + ':' + loginPass));
    setRole('DOCTOR');
    setShowLogin(false);
  };

  const loadDoctors = async () => {
    const r = await fetch('/api/doctor', { headers: { Authorization: authHeader } });
    const list = await r.json();
    setDoctors(list);
    if (list.length) setDoctorId(list[0].id);
  };

  const createAppointment = async (e) => {
    e.preventDefault();
    const data = {
      doctorId: parseInt(doctorId),
      appointmentTime: apptTime
    };
    const r = await fetch('/api/patient/appointments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authHeader
      },
      body: JSON.stringify(data)
    });
    const appt = await r.json();
    alert('Created appointment ' + appt.id);
  };

  const loadPatientAppointments = async () => {
    const r = await fetch('/api/patient/appointments', { headers: { Authorization: authHeader } });
    setPatientAppts(await r.json());
  };

  const loadDoctorAppointments = async () => {
    const r = await fetch('/api/doctor/appointments', { headers: { Authorization: authHeader } });
    setDoctorAppts(await r.json());
  };

  const updateAppointment = async (id, status, notes) => {
    await fetch(`/api/doctor/appointments/${id}/status`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authHeader
      },
      body: JSON.stringify({ status, notes })
    });
    loadDoctorAppointments();
  };

  useEffect(() => {
    if (role === 'PATIENT') loadDoctors();
    if (role === 'DOCTOR') loadDoctorAppointments();
  }, [role]);

  return (
    <div id="container">
      <div id="top-bar">
        <button onClick={() => setShowLogin(true)}>Login</button>
        <button onClick={() => setShowRegister(true)}>Register</button>
      </div>
      <h1>Hospital Appointment System</h1>

      {showLogin && (
        <div className="modal-overlay" onClick={() => setShowLogin(false)}>
          <form className="modal" onClick={e => e.stopPropagation()}>
            <h2>Login</h2>
            <input value={loginUser} onChange={e => setLoginUser(e.target.value)} placeholder="Username" />
            <input type="password" value={loginPass} onChange={e => setLoginPass(e.target.value)} placeholder="Password" />
            <div style={{display:'flex',justifyContent:'space-between'}}>
              <button onClick={loginPatient}>Patient Login</button>
              <button onClick={loginDoctor}>Doctor Login</button>
            </div>
            <button type="button" onClick={() => setShowLogin(false)}>Close</button>
          </form>
        </div>
      )}

      {showRegister && (
        <div className="modal-overlay" onClick={() => setShowRegister(false)}>
          <form className="modal" onSubmit={registerPatient} onClick={e => e.stopPropagation()}>
            <h2>Register Patient</h2>
            <input name="regUsername" placeholder="Username" />
            <input name="regPassword" type="password" placeholder="Password" />
            <input name="regName" placeholder="Name" />
            <input name="regAge" type="number" placeholder="Age" />
            <input name="regContact" placeholder="Contact" />
            <button type="submit">Register</button>
            <button type="button" onClick={() => setShowRegister(false)}>Close</button>
          </form>
        </div>
      )}
      
      {role === 'PATIENT' && (
        <div id="patient-area">
          <h2>Patient Area</h2>
          <form onSubmit={createAppointment}>
            <select value={doctorId} onChange={e => setDoctorId(e.target.value)}>
              {doctors.map(d => (
                <option key={d.id} value={d.id}>{d.name} ({d.specialty})</option>
              ))}
            </select>
            <input type="datetime-local" value={apptTime} onChange={e => setApptTime(e.target.value)} />
            <button type="submit">Create Appointment</button>
          </form>
          <button onClick={loadPatientAppointments}>My Appointments</button>
          <pre>{JSON.stringify(patientAppts, null, 2)}</pre>
        </div>
      )}

      {role === 'DOCTOR' && (
        <div id="doctor-area">
          <h2>Doctor Area</h2>
          <button onClick={loadDoctorAppointments}>Load Appointments</button>
          <div>
            {doctorAppts.map(ap => (
              <div key={ap.id} style={{margin:'10px 0'}}>
                <pre>{JSON.stringify(ap)}</pre>
                <select defaultValue={ap.status} id={`status-${ap.id}`}>
                  {['PENDING','APPROVED','REJECTED'].map(s => (
                    <option key={s} value={s}>{s}</option>
                  ))}
                </select>
                <input placeholder="Notes" id={`note-${ap.id}`} />
                <button onClick={() => updateAppointment(ap.id,
                    document.getElementById(`status-${ap.id}`).value,
                    document.getElementById(`note-${ap.id}`).value)}>Update</button>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

ReactDOM.createRoot(document.getElementById('root')).render(<App />);
