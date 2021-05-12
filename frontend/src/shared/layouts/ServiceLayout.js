import React from 'react';
import ReactTooltip from 'react-tooltip';

import Aside from '@/components/Aside/Aside';
import Footer from '@/components/Footer/Footer';

export default function ServiceLayout ({ children }) {
    return (
        <div className="wrapper">
            <ReactTooltip />
            <main>
            <Aside />
            <div className="content">
                <div className="row">
                    { children }
                </div>
            </div>
            </main>
            <Footer />
        </div>
    );
};