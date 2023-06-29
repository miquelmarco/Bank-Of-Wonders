let { createApp } = Vue;
createApp({
    data() {
        return {
            clientName: '',
            accounts: [],
            loans: [],
            cards: []
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(`http://localhost:8080/api/clients/current`)
                .then(res => {
                    this.client = res.data
                    this.clientName = this.client.firstName + ' ' + this.client.lastName
                    this.accounts = this.client.accounts.sort((a,b) => a.id - b.id)
                    this.loans = this.client.loans.sort((a,b) => a.id - b.id)
                    this.cards = this.client.cards.sort((a,b) => a.id - b.id)
                    console.log(this.cards)
                }).catch(err => console.log(err))
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