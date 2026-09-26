const studentCard = document.getElementById("student");
const teacherCard = document.getElementById("teacher");
const continueButton = document.getElementById("continue-button");

studentCard.addEventListener("click", function(){
    studentCard.classList.add("selected");
    teacherCard.classList.remove("selected");
    continueButton.disabled = false;
});

teacherCard.addEventListener("click", function(){
    teacherCard.classList.add("selected");
    studentCard.classList.remove("selected");
    continueButton.disabled = false;
});