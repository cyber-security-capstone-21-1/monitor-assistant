import React, { useState, useEffect } from 'react';
import axios from 'axios';
import PageHeader from '@/components/PageHeader/PageHeader';
import IntelligenceCard from '@/components/IntelligenceCard/IntelligenceCard';

function IntList () {
    const [intList, setIntList] = useState([]);
    useEffect(() => {
        async function loadIntelligences() {
            const intelligences = await axios.get("http://3.36.186.72/api/intelligences/");
            setIntList([...intelligences.data]);
        }
        loadIntelligences();
    }, []);

    return (
        <>
            <PageHeader title="첩보 목록" desc="첩보 목록" />
            <section style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)", gap: "20px" }}>
                {intList.map(({ title, site, author, created_at, archived_UID, id, content }, index) => {
                    return (
                        <IntelligenceCard
                            title={title}
                            site={site}
                            author={author}
                            created_at={created_at}
                            uid={archived_UID}
                            content={content}
                        />
                    )
                })}
            </section>
        </>
    );
}

export default IntList;