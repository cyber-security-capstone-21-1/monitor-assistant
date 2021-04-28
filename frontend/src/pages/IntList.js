import React, { useState, useEffect } from 'react';
import axios from 'axios';
import PageHeader from '@/components/PageHeader/PageHeader';

function IntList () {
    const [intList, setIntList] = useState([]);

    useEffect(() => {
        async function get() {
            const intelligences = await axios.get("/api/monitor/api/intelligences/");
            setIntList([...intelligences.data]);
        }
        get();
    }, []);

    return (
        <>
            <PageHeader title="첩보 목록" desc="첩보 목록" />
            <table>
                <thead>
                    <tr>
                        <th>제목</th>
                        <th>사이트명</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>작업</th>
                    </tr>
                </thead>
                <tbody>
            {intList.map((int, index) => {
                return (
                    <tr key={index}>
                        <td>{int.title}</td>
                        <td>{int.site}</td>
                        <td>{int.author}</td>
                        <td>{new Date(int.created_at).toLocaleString()}</td>
                        <td>
                            <a>PDF</a>
                            <a>삭제</a>
                        </td>
                    </tr>
                )
            })}
                </tbody>
            </table>
        </>
    );
}

export default IntList;
