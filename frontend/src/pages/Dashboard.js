import React, { Component } from 'react'

class Dashboard extends Component {
    render () {
        return (
            <>
                <header>대시보드</header>
                <iframe src="http://localhost:5601"></iframe>
                <iframe src="http://localhost:8080/api/monitor/?keyword=테스트"></iframe>
            </>
        )
    }
}

export default Dashboard;