import React, { Component } from 'react';

class Navigation extends Component {
    render () {
        return (
            <nav>
                <Route path="/" exact={true} />
            </nav>
        )
    }
}

export default Navigation;