
let typingTimeout = null;

export function typing(element, text) {
    // safe guard
    if(typingTimeout !== null){
        clearTimeout(typingTimeout);
        typingTimeout = null
    }

    let i = 0;
    element.textContent = "";
    function loadText() {
        if (i < text.length) {
            element.textContent += text.charAt(i);
            i++;
            typingTimeout = setTimeout(loadText, 100); // pause for 100 ms n recall
        }
    }
    loadText();
}


export function observe(element, callback) {

    // check if el inside vp
    const observer = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting) {

            callback();
            observer.disconnect();
        }
    });

    observer.observe(element);
} 