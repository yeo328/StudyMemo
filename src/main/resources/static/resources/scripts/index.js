
const memoContainer = document.getElementById('memoContainer');
const deleteButtons = memoContainer.querySelectorAll('[rel="delete"]');
const modifyButtons = memoContainer.querySelectorAll('[rel="modify"]');
const modifyCover = document.getElementById('modifyCover');
const modifyForm = document.getElementById('modifyForm');

deleteButtons.forEach(deleteButton => {
    deleteButton.addEventListener('click', (e) => {
        e.preventDefault();

        const index = deleteButton.dataset.index;
        const xhr = new XMLHttpRequest();
        xhr.open('DELETE', `./?index=${index}`);
        xhr.onreadystatechange = () => {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseText = xhr.responseText; // 'true' | 'false'
                    if (responseText === 'true') {
                        location.href += '';
                    } else {
                        alert('알 수 없는 이유로 삭제하지 못하였습니다.\n\n이미 삭제된 메모일 수도 있습니다.');
                    }
                } else {
                    alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해 주세요.');
                }
            }
        };
        xhr.send();
    });
});

modifyCover.addEventListener('click', () => {
    modifyCover.classList.remove('visible');
    modifyForm.classList.remove('visible');
});
modifyForm.querySelector('[rel="close"]').addEventListener('click', () => {
    modifyCover.classList.remove('visible');
    modifyForm.classList.remove('visible');
});


modifyForm.onsubmit = e => {
    e.preventDefault();
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('index', modifyForm['index'].value);
    formData.append('text', modifyForm['text'].value);
    xhr.open('PATCH', './');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) { // 요청이 완료되면
            if (xhr.status >= 200 && xhr.status < 300) { // 응답 코드가 성공(2xx)이면
                const responseText = xhr.responseText; // 서버에서 받은 응답 결과 true or false
                if (responseText === 'true') { // 수정이 성공적으로 이루어졌을 때
                    location.href += ''; // 현재 페이지를 새로고침
                } else { // 서버에서 false를 반환했을 때
                    alert('알 수 없는 이유로 수정하지 못하였습니다.'); // 알림창 띄우기
                }
            } else { // 응답 코드가 실패(4xx, 5xx)이면
                alert('서버와 통신하지 못하였습니다.'); // 알림창 띄우기
            }
        }
    };
    // 여기 부터
    // 요청 방식 : PATCH
    // 요청 주소(./)
    // 응답 구분 : 'true' 로 응답오면 수정 성공, 'false'로 응답오면 수정 실패
    // 제발 @ResponseBody 쓰고 ModelAndView 쓰지 마세요
    // 여기 까지
    xhr.send(formData);
};
modifyButtons.forEach(modifyButton => {
    modifyButton.addEventListener('click', e => {
        e.preventDefault();

        modifyCover.classList.add('visible');
        modifyForm['index'].value = modifyButton.dataset.index;
        modifyForm['text'].value = modifyButton.dataset.text;
        modifyForm['text'].focus();
        modifyForm['text'].select();
        modifyForm.classList.add('visible');
    });
});