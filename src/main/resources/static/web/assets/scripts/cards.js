let { createApp } = Vue;
createApp({
    data() {
        return {
            clientName: '',
            cards: [],
            debitCards: [],
            creditCards: []
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
                    this.cards = this.client.cards.sort((a,b) => a.id - b.id)
                    this.debitCards = this.client.cards.filter(card => card.type == "DEBIT")
                    this.creditCards = this.client.cards.filter(card => card.type == "CREDIT")
                }).catch(err => console.error(err))
        },
        changeCardColor(card) {
            if (card.color === 'SILVER') {
                return 'silverBg';
            } else if (card.color === 'GOLD') {
                return 'goldBg';
            } else if (card.color === 'TITANIUM') {
                return 'titaniumBg'
            }
        },
        sessionLogOut() {
            axios.post("/api/logout")
                .then(res => {
                    if (res.status == 200) {
                        Swal.fire({
                            position: 'top-center',
                            title: 'Bye bye!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        setTimeout(() => {
                            window.location.href = "/web/index.html";
                        }, 1800)
                    }
                    // window.location.href = "/web/index.html"
                    console.log(sres)
                }).catch(err => { console.log(err) })
        }
    }
}).mount("#app")