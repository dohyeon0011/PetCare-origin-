function login() {
    const loginId = document.getElementById('loginId').value;
    const password = document.getElementById('password').value;

    fetch('/pets-care/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ loginId, password }), // JSON으로 데이터 전송
    })
        .then((response) => {
            if (!response.ok) {
                // 응답 코드가 200-299 범위가 아닌 경우 에러 처리
                return response.json().then((error) => {
                    throw new Error(error.message || '로그인 실패');
                });
            }
            return response.json(); // JSON 응답 반환
        })
        .then((result) => {
            alert(`로그인 성공! 환영합니다, ${result.name}님!`);
            window.location.href = '/pets-care/main'; // 메인 페이지로 리다이렉트
        })
        .catch((error) => {
            // 에러 메시지 출력
            console.error('로그인 요청 실패:', error);
            alert(error.message || '로그인 요청 중 문제가 발생했습니다.');
        });
}
