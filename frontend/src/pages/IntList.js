import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';

import PageHeader from '@/components/PageHeader/PageHeader';
import IntelligenceCard from '@/components/IntelligenceCard/IntelligenceCard';

import Constants from '@/shared/constants';

function IntList () {
    const [intList, setIntList] = useState([]);
    useEffect(() => {
        Swal.fire({
            title: '목록 로딩 중',
            html: '데이터를 불러오고 있습니다.',
            allowOutsideClick: false,
            didOpen: async () => {
                Swal.showLoading();
                const result = await axios.get(`${Constants.SPRING_BACKEND.APIs.INTLIST}`)
                setIntList([...result.data]);
                Swal.close();
            },
        });
    }, []);

    return (
        <>
            <PageHeader
                title="첩보 리스트"
                desc="수집한 게시물의 내용이 담겨있습니다."
            />
            <section className="section section__intelligence_list">
                {intList.map(({ title, site, author, created_at, uid, id, content, action_plan }, index) => {
                    return (
                        <IntelligenceCard
                            title={title}
                            site={site}
                            author={author}
                            created_at={created_at}
                            uid={uid}
                            content={content}
                            action_plan={action_plan}
                        />
                    )
                })}
            </section>
        </>
    );
}

export default IntList;