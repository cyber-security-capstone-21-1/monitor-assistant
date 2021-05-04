import React from 'react';
import { Route } from 'react-router-dom';

export default function RouteWithLayout ({ layout, component, ...rest }) {
    return (<Route {...rest} render={(props) => React.createElement(layout, props, React.createElement(component, props))}/>);
}