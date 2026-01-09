const img = document.querySelector("#image-slider");
const left = document.querySelector("#left-arrow");
const right = document.querySelector("#right-arrow");

const topicDesc = document.querySelector("#topic-desc");
const topicTitle = document.querySelector("#topic-title");

const sortClose = document.querySelector("#sort-close");
const sortLarge = document.querySelector("#sort-large");

const objectName = document.querySelector("#object-name");

const viewModeText = document.querySelector("#view-mode");

const factType = document.querySelector("#type");
const factDistance = document.querySelector("#distance");
const factDiameter = document.querySelector("#diameter");

import { BLACK_HOLES_CLOSEST } from "./data.js";
import { BLACK_HOLES_LARGEST } from "./data.js";
import { GALAXY_CLOSEST } from "./data.js";
import { GALAXY_LARGEST } from "./data.js";
import { NEBULA_CLOSEST } from "./data.js";
import { NEBULA_LARGEST } from "./data.js";

import { observe, typing } from "./text-effects.js";

export const state = {
  Topic: "BLACKHOLE",
  Mode: "CLOSEST"
}

// get topic from url
const params = new URLSearchParams(window.location.search);
const topic = params.get("topic");

state.Topic = topic;

// set topic title
if (state.Topic === "BLACKHOLE") {
  observe(topicTitle, () => {
    typing(topicTitle, "BLACK HOLE");
  })
} else {
  observe(topicTitle, () => {
    typing(topicTitle, state.Topic);
  })
}

function resolveData() {
  switch (state.Topic) {
    case "BLACKHOLE":
      if (state.Mode === "CLOSEST") {
        viewModeText.textContent = "Viewing closest Black Holes";
        return BLACK_HOLES_CLOSEST;
      }
      if (state.Mode === "LARGEST") {
        viewModeText.textContent = "Viewing largest Black Holes";
        return BLACK_HOLES_LARGEST;
      }
      break;

    case "GALAXY":
      if (state.Mode === "CLOSEST") {
        viewModeText.textContent = "Viewing closest Galaxies";
        return GALAXY_CLOSEST;
      }
      if (state.Mode === "LARGEST") {
        viewModeText.textContent = "Viewing largest Galaxies";
        return GALAXY_LARGEST;
      }
      break;

    case "NEBULA":
      if (state.Mode === "CLOSEST") {
        viewModeText.textContent = "Viewing closest Nebulae";
        return NEBULA_CLOSEST;
      }
      if (state.Mode === "LARGEST") {
        viewModeText.textContent = "Viewing largest Nebulae";
        return NEBULA_LARGEST;
      }
      break;
  }

  return {};
}


let index = 0;
let hashMap = {};
let keys = []; // all keys of hash map in an arr


pageInit();

function pageInit() {

  hashMap = resolveData();
  keys = Object.keys(hashMap);

  setData();
}



// sort btns
sortClose.addEventListener("click", () => {

  index = 0;
  state.Mode = "CLOSEST";
  pageInit();
});

sortLarge.addEventListener("click", () => {

  index = 0;
  state.Mode = "LARGEST";
  pageInit();
})



left.addEventListener("click", () => {

  index--;
  if (index < 0) index = keys.length - 1;  // go to last img
  setData();
});

right.addEventListener("click", () => {

  index++;
  if (index >= keys.length) index = 0; // go to first img
  setData();
});


// set the data based on which img is being viewed
function setData() {

  let data = hashMap[keys[index]];

  objectName.textContent = data.name;
  img.src = data.image;
  topicDesc.innerHTML = data.desc;

  factType.textContent = data.type;
  factDistance.textContent = data.distance;
  factDiameter.textContent = data.diameter;
}









