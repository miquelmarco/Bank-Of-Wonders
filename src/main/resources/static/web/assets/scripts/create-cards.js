let { createApp } = Vue;
createApp({
    data() {
        return {
            cardType:'',
            cardColor:''
        }
    },
    created() {
    },
    methods: {
        createCard() {
            console.log(this.cardColor, this.cardType)
            axios.post("/api/clients/current/cards",`cardType=${this.cardType}&cardColor=${this.cardColor}`)
                .then(res => {
                    console.log("esteConsoleLogNoHaceNada")
                    window.location.href = "/web/pages/cards.html"
                }).catch(err => console.error(err))
        },
        sessionLogOut() {
            axios.post("/api/logout")
            .then(res => {
                window.location.href = "/web/index.html"
                console.log("signedOUT")
            }).catch(err => {console.log(err)})
        }
    }
}).mount("#app")