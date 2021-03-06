import React from 'react';
import PageHeader from '@/components/PageHeader/PageHeader';

function Dashboard (props) {
    return (
        <>
            <PageHeader
                title="데이터 인사이트"
                desc="수집된 데이터 통계입니다."
            />
            <iframe
                style={{
                    width: "100%",
                    height: "1000px"
                }}
                title="monitor-assistant plugin - kibana"
                src="https://search-monitor-assistant-es-q7phnn6tbzv2usgv7qxouxloxu.ap-northeast-2.es.amazonaws.com/_plugin/kibana/app/dashboards#/view/0986bcd0-8fc0-11eb-98b3-cb77ad4fc98b?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-15m%2Cto%3Anow))"
            ></iframe>
        </>
    );
}

export default Dashboard;