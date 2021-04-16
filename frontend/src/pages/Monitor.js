import React from 'react';
import PageHeader from '@/components/PageHeader/PageHeader';

import Swal from 'sweetalert2';

function Monitor (props) {
    
    const search = () => {
        Swal.fire({
            title: '검색 중입니다...',
            html: '검색이 완료되면 창이 자동으로 사라집니다.',
            didOpen: () => {
                Swal.showLoading();
            },
            willClose: () => {
            }
            }).then((result) => {
            /* Read more about handling dismissals below */
            if (result.dismiss === Swal.DismissReason.timer) {
                console.log('I was closed by the timer')
            }
        });
    };

    return (
        <>
            <PageHeader title="모니터링" desc="모니터링" />
            <input name="keyword" type="text" />
            <button onClick={search}>검색</button>
        </>
    );
}

export default Monitor;
