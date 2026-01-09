const namee = document.querySelector("#feedback-name");
const email = document.querySelector("#feedback-email");
const message = document.querySelector("#feedback-message");
const sendBtn = document.querySelector("#feedback-btn");

const notification = document.querySelector("#notification");


sendBtn.addEventListener("click", ()=>{

    if (!namee.value || !email.value || !message.value) {
    return;
}

    namee.value = "";
    email.value = "";
    message.value = "";

  notification.classList.add("show");

setTimeout(() => {
    notification.classList.remove("show");
}, 2000);

})