function search() {
    const titleInput = document.getElementById("title").value
    const circleInput = document.getElementById("circle").value
    //TODO song filtering
    alert(`Title = ${titleInput} \nCircle = ${circleInput}`)
}

const name = document.getElementById("app").getAttribute("data-name")

const EventHandling = {
    data() {
        return {
            message: `Hello ${name}!`,
            counter: 0
        }
    },
    mounted() {
        // setInterval(() => {
        //     this.counter++
        // }, 1000)
    },
    methods: {
        reverseMessage() {
            this.message = this.message
                .split('')
                .reverse()
                .join('')
        }
    }
}

Vue.createApp(EventHandling).mount('#app')

const songElems = Array.from(document.getElementsByClassName("songs-content"))

const songsAttr = songElems.map(elem => elem.getAttribute("data-name"))
songsAttr.forEach(elem => console.log(elem))
