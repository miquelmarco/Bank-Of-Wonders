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
            axios.get(`http://localhost:8080/api/clients/1`)
                .then(res => {
                    this.client = res.data
                    this.cards = this.client.cards.sort((a,b) => a.id - b.id)
                    this.debitCards = this.client.cards.filter(card => card.type == "DEBIT").sort(a, b => a.id - b.id)
                    this.creditCards = this.client.cards.filter(card => card.type == "CREDIT").sort(a, b => a.id - b.id)
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
        }
    }
}).mount("#app")