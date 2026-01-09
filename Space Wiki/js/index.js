import { PLANETDATA } from "./data.js";
import { TOPICDATA } from "./data.js";
import { typing, observe } from "./text-effects.js";

// planets
const planetBtns = document.querySelectorAll(".planet-btn");

const planetImage = document.querySelector("#planet-img");
const planetDesc = document.querySelector("#planet-desc");
const planetAudio = document.querySelector("#audio");
const planetTitle = document.querySelector("#planet-title");

const factType = document.querySelector("#type");
const factDistance = document.querySelector("#distance");
const factDiameter = document.querySelector("#diameter");
const factTemp = document.querySelector("#temp");
const factText = document.querySelector("#fact-text");
const audioDesc = document.querySelector("#audio-desc");

// topic
const headerText = document.querySelector("#header-text");
const img = document.querySelector("#image-slider");
const left = document.querySelector("#left-arrow");
const right = document.querySelector("#right-arrow");
const topicDesc = document.querySelector("#topic-desc");
const topicTitle = document.querySelector("#topic-title");
const learnMoreBtn = document.querySelector("#learn-more-btn");


// get length
const keys = Object.keys(TOPICDATA);
let index = 0;


pageInit();
function pageInit() {

    // default
    setData(PLANETDATA["Sun"]);
    setTopicData();
}


planetBtns.forEach(btn => {
    btn.addEventListener("click", () => {

        // get name of the planet then data
        const planet = btn.dataset.planet;
        const data = PLANETDATA[planet];

        setData(data);
    });
});



// load header text when its in view
observe(headerText, () => {
    typing(headerText, "EXPLORE MORE");
});


// riderect
learnMoreBtn.addEventListener("click", () => {
    const topic = keys[index];
    window.location.href = `explore-page.html?topic=${topic}`;
})


left.addEventListener("click", () => {

    index--;
    if (index < 0) index = keys.length - 1;  // go to last img
    setTopicData();
});

right.addEventListener("click", () => {

    index++;
    if (index >= keys.length) index = 0; // go to first img
    setTopicData();
});



function setData(data) {
    // hide incase audio was playing
    audioDesc.classList.add("hidden");

    // set data
    planetTitle.textContent = data.Title;
    planetDesc.textContent = data.Desc;

    planetAudio.src = data.Audio;
    audioDesc.textContent = data.AudioDesc;

    planetImage.src = data.Image;

    factType.textContent = data.Type;
    factDistance.textContent = data.Distance
    factDiameter.textContent = data.Diameter
    factTemp.textContent = data.Temperature;
    factText.textContent = data.Fact;
}



function setTopicData() {
    const data = TOPICDATA[keys[index]];

    img.src = data.Image;
    topicDesc.innerHTML = data.Desc;
    topicTitle.textContent = data.Title;
}
