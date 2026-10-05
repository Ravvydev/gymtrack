let userType = null;
let currentStepIndex = 0;
let stepsSequence = [];
const formData = {
    personal: {},
    school: {},
    objectives: [],
    professional: {},
    classes: [],
    preferences: {}
};

// Define sequences based on user type
const ALUNO_STEPS = ['step-user-type', 'step-personal-info', 'step-school-info', 'step-objectives', 'step-review', 'step-success'];
const PROFESSOR_STEPS = ['step-user-type', 'step-personal-info', 'step-prof-info', 'step-classes', 'step-preferences', 'step-review', 'step-success'];

document.addEventListener('DOMContentLoaded', () => {
    // Initialize first step
    stepsSequence = ALUNO_STEPS; // default to avoid errors
    showStep(0);

    document.getElementById('back-button').addEventListener('click', prevStep);
});

function selectUserType(type) {
    userType = type;
    
    // Update UI selection
    document.getElementById('card-aluno').classList.remove('selected');
    document.getElementById('card-professor').classList.remove('selected');
    document.getElementById(`card-${type}`).classList.add('selected');

    // Enable continue button
    document.getElementById('continue-button').disabled = false;

    // Set sequence
    stepsSequence = type === 'aluno' ? ALUNO_STEPS : PROFESSOR_STEPS;
}

function showStep(index) {
    // Hide all steps
    document.querySelectorAll('.step').forEach(el => el.style.display = 'none');
    
    // Show current step
    const stepId = stepsSequence[index];
    document.getElementById(stepId).style.display = 'block';

    // Header visibility
    const header = document.getElementById('header');
    if (index === 0 || stepId === 'step-success') {
        header.style.display = 'none';
    } else {
        header.style.display = 'flex';
        // Progress bar logic
        const totalNavigableSteps = stepsSequence.length - 1; // exclude success step from progress max
        const progressPercent = (index / totalNavigableSteps) * 100;
        document.getElementById('progress-fill').style.width = `${progressPercent}%`;
        document.getElementById('progress-text').innerText = `${index}/${totalNavigableSteps}`;
    }

    // Button text logic
    const btn = document.getElementById('continue-button');
    if (stepId === 'step-success') {
        document.getElementById('footer-actions').style.display = 'none';
    } else if (stepId === 'step-review') {
        btn.innerText = 'Finalizar cadastro';
        btn.disabled = false;
        generateReview();
    } else {
        btn.innerText = 'Continuar';
        document.getElementById('footer-actions').style.display = 'block';
        if (index === 0 && !userType) {
            btn.disabled = true;
        } else {
            btn.disabled = false;
        }
    }
}

function nextStep() {
    // Collect data before moving forward
    saveCurrentStepData();

    if (currentStepIndex < stepsSequence.length - 1) {
        currentStepIndex++;
        showStep(currentStepIndex);
    }
}

function prevStep() {
    if (currentStepIndex > 0) {
        currentStepIndex--;
        showStep(currentStepIndex);
    }
}

function saveCurrentStepData() {
    const currentStepId = stepsSequence[currentStepIndex];

    if (currentStepId === 'step-personal-info') {
        formData.personal = {
            name: document.getElementById('pi-name').value,
            birth: document.getElementById('pi-birth').value,
            gender: document.getElementById('pi-gender').value,
            state: document.getElementById('pi-state').value,
            city: document.getElementById('pi-city').value
        };
    }
    else if (currentStepId === 'step-school-info') {
        formData.school = {
            network: document.getElementById('si-network').value,
            schoolName: document.getElementById('si-school').value,
            grade: document.getElementById('si-grade').value,
            className: document.getElementById('si-class').value
        };
    }
    else if (currentStepId === 'step-objectives') {
        const checkboxes = document.querySelectorAll('.obj-checkbox:checked');
        formData.objectives = Array.from(checkboxes).map(cb => cb.value);
    }
    else if (currentStepId === 'step-prof-info') {
        formData.professional = {
            formation: document.getElementById('pro-formation').value,
            cref: document.getElementById('pro-cref').value
        };
    }
    else if (currentStepId === 'step-preferences') {
        formData.preferences = {
            notifications: document.getElementById('pref-notifications').checked,
            reports: document.getElementById('pref-reports').checked
        };
    }
}

// Professor Classes logic
function addClass() {
    const grade = document.getElementById('class-grade').value;
    const name = document.getElementById('class-name').value;
    const shift = document.getElementById('class-shift').value;

    if (grade && name && shift) {
        formData.classes.push({ grade, name, shift });
        renderClasses();
        // Clear inputs
        document.getElementById('class-grade').value = '';
        document.getElementById('class-name').value = '';
        document.getElementById('class-shift').value = 'Manhã';
    }
}

function removeClass(index) {
    formData.classes.splice(index, 1);
    renderClasses();
}

function renderClasses() {
    const list = document.getElementById('classes-list');
    list.innerHTML = '';
    formData.classes.forEach((c, index) => {
        const div = document.createElement('div');
        div.className = 'list-item';
        div.innerHTML = `
            <span>${c.grade} - Turma ${c.name} (${c.shift})</span>
            <span class="remove-item" onclick="removeClass(${index})">X</span>
        `;
        list.appendChild(div);
    });
}

function generateReview() {
    const reviewBox = document.getElementById('review-content');
    let html = '';

    // Personal Info
    html += `
        <div class="review-section">
            <h3>Informações Pessoais</h3>
            <div class="review-item"><strong>Nome:</strong> ${formData.personal.name || '-'}</div>
            <div class="review-item"><strong>Data de Nasc.:</strong> ${formData.personal.birth || '-'}</div>
            <div class="review-item"><strong>Sexo:</strong> ${formData.personal.gender || '-'}</div>
            <div class="review-item"><strong>Local:</strong> ${formData.personal.city || '-'} - ${formData.personal.state || '-'}</div>
        </div>
    `;

    if (userType === 'aluno') {
        html += `
            <div class="review-section">
                <h3>Informações Escolares</h3>
                <div class="review-item"><strong>Rede:</strong> ${formData.school.network || '-'}</div>
                <div class="review-item"><strong>Escola:</strong> ${formData.school.schoolName || '-'}</div>
                <div class="review-item"><strong>Série:</strong> ${formData.school.grade || '-'}</div>
                <div class="review-item"><strong>Turma:</strong> ${formData.school.className || '-'}</div>
            </div>
            <div class="review-section">
                <h3>Objetivos</h3>
                <div class="review-item">${formData.objectives.length > 0 ? formData.objectives.join(', ') : '-'}</div>
            </div>
        `;
    } else {
        html += `
            <div class="review-section">
                <h3>Informações Profissionais</h3>
                <div class="review-item"><strong>Formação:</strong> ${formData.professional.formation || '-'}</div>
                <div class="review-item"><strong>CREF:</strong> ${formData.professional.cref || '-'}</div>
            </div>
            <div class="review-section">
                <h3>Turmas para Acompanhar</h3>
                ${formData.classes.length > 0 
                    ? formData.classes.map(c => `<div class="review-item">- ${c.grade} | Turma ${c.name} | ${c.shift}</div>`).join('')
                    : '<div class="review-item">Nenhuma turma adicionada.</div>'}
            </div>
            <div class="review-section">
                <h3>Preferências</h3>
                <div class="review-item"><strong>Notificações por e-mail:</strong> ${formData.preferences.notifications ? 'Sim' : 'Não'}</div>
                <div class="review-item"><strong>Relatórios automáticos:</strong> ${formData.preferences.reports ? 'Sim' : 'Não'}</div>
            </div>
        `;
    }

    reviewBox.innerHTML = html;
}
