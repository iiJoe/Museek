function btnClick() {
    alert("ಠ_ಠ");
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
        setInterval(() => {
            this.counter++
        }, 1000)
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
