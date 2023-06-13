let { createApp } = Vue;

createApp({
    data() {
        return {
            transactions: [],
            queryId: '',
            transInDescenOrder:[]
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            this.queryId = new URLSearchParams(location.search).get('id')
            axios.get(`http://localhost:8080/api/accounts/${this.queryId}`)
                .then(res => {
                    this.transactions = res.data.transactions
                    console.log(this.transactions)
                }).catch(err => console.error(err))
        },
        changeClassByTransaction(transaction) {
            if (transaction.type === 'DEBIT') {
                return 'debit';
            } else if (transaction.type === 'CREDIT') {
                return 'credit';
            } else {
                return 'standarTable';
            }
        }
    },
    computed: {
        orderedTransactions () {
            let transInDescenOrder = [...this.transactions]
            transInDescenOrder.sort((a, b) => b.id - a.id)
            return transInDescenOrder
        }
    }
}).mount("#app")