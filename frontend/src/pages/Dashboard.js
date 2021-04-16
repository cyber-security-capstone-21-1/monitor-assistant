import React from 'react';
import PageHeader from '@/components/PageHeader/PageHeader';

function Dashboard (props) {
    return (
        <>
            <PageHeader title="대시보드" desc="데이터 인사이트" />
            <iframe src="https://search-monitor-assistant-es-q7phnn6tbzv2usgv7qxouxloxu.ap-northeast-2.es.amazonaws.com/_plugin/kibana/app/dashboards#/view/0986bcd0-8fc0-11eb-98b3-cb77ad4fc98b?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-15m%2Cto%3Anow))"></iframe>
            <iframe src="http://localhost:8080/api/monitor/?keyword=테스트"></iframe>
        </>
    );
}

export default Dashboard;