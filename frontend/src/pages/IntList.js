import React, { useState, useEffect } from 'react';
import axios from 'axios';

import PageHeader from '@/components/PageHeader/PageHeader';
import IntelligenceCard from '@/components/IntelligenceCard/IntelligenceCard';

import Constants from '@/shared/constants';

function IntList () {
    const [intList, setIntList] = useState([]);
    useEffect(() => {
        Swal.fire({
            title: '목록 로딩 중',
            html: '데이터를 불러오고 있습니다.',
            didOpen: () => {
                Swal.showLoading();
                axios.get(`${Constants.ENDPOINT}${Constants.SPRING_BACKEND.APIs.INTLIST}`)
                .then(({ data }) => {
                    setIntList([...data]);
                });
            },
        });
    }, []);

    return (
        <>
            <PageHeader title="첩보 목록" desc="첩보 목록" />
            <section style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)", gap: "20px" }}>
                {intList.map(({ title, site, author, created_at, uid, id, content }, index) => {
                    return (
                        <IntelligenceCard
                            title={title}
                            site={site}
                            author={author}
                            created_at={created_at}
                            uid={uid}
                            content={content}
                        />
                    )
                })}
            </section>
        </>
    );
}

export default IntList;