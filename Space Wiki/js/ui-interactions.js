const facts = document.querySelectorAll(".fact-item");

const audioBtn = document.querySelector("#audio-btn");
const audio = document.querySelector("#audio");
const audioDesc = document.querySelector("#audio-desc");

const readMore = document.querySelector("#toggle-about");
const aboutUs = document.querySelector("#about-text");

const left = document.querySelector("#left-arrow");
const right = document.querySelector("#right-arrow");


if (audioBtn && audio && audioDesc) {
audioBtn.addEventListener("click", () => {
    if (!audio.paused) {
        audio.pause();
        audio.currentTime = 0;
        audioDesc.classList.add('hidden');

    } else {

        audio.play();
        audioDesc.classList.remove('hidden');
    }
});

audio.addEventListener("ended", () => {
    audioDesc.classList.add("hidden");
});
}




// handle fact buttons
facts.forEach(fact => {
    fact.addEventListener("click", () => {
        const label = fact.querySelector("label");
        const desc = fact.querySelector("p");

       
        label.classList.toggle("hidden");
        desc.classList.toggle("hidden");
    });
});




// handle read more in abt us
readMore.addEventListener("click", ()=>{
   aboutUs.classList.toggle("collapsed");
})
