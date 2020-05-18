import axios from 'axios'

const httpClient = axios.create({
    baseURL: 'http://localhost:8080'
})

class ApiService {

    constructor(apiurl) {
        this.apiurl = apiurl
    }

    post(url, objeto) {
        const requesUrl = `${this.apiurl}${url}`
        console.log(requesUrl)
        return httpClient.post(requesUrl, objeto)

    }

    put(url, objeto) {
        const requesUrl = `${this.apiurl}${url}`
        return httpClient.put(requesUrl, objeto)
    }

    delete(url) {
        const requesUrl = `${this.apiurl}${url}`
        return httpClient.delete(requesUrl)
    }

    get(url) {
        const requesUrl = `${this.apiurl}${url}`
        return httpClient.get(requesUrl)
    }
}

export default ApiService;