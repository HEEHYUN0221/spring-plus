# SPRING PLUS

<details>
  <summary>Lv1</summary>
Lv1 - 1. 코드 개선 퀴즈 - @Transactional의 이해

- 기존에 @Transactional(readOnly = true)라고 되있는 부분에서 오류가 발생했다.
- @Transcational로 변경하여 문제를 해결했다.
![image](https://github.com/user-attachments/assets/9f964053-3108-45ee-a6eb-7f0b493b1dfc)

Lv1 - 2. JWT의 이해

- User 테이블에 nickname 추가했다.
- JWT에 nickname 추가했다.

Lv1 - 3. AOP의 이해

- UserAdminController 클래스의 changeUserRole() 메소드가 실행 전 동작할 수 있도록 @After 를 @Before로 변경했다.

Lv1 - 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해

- 기존 코드의 기댓값 HttpStatus.OK가 문제를 일으켰다.
- HttpStatus.valueOf(400) 으로 해당 컨트롤러의 의도대로 수정했다.

Lv1 - 5. 코드 개선 퀴즈 - JPA의 이해

- 할일 검색 시 weather, 수정일 기준 조회 내림차순 기능을 추가했다.

</details>

<details>
  <summary>Lv2</summary>
Lv2 - 1. JPA Cascade

- 할 일을 새로 저장할 시, 할일을 생성한 유저가 담당자로 자동 등록 될 수 있도록 수정해야 한다.
- cascade = CascadeType.PERSIST 옵션을 지정했다.

Lv2 - 2. N+1

- getComments() 의 N+1 문제가 발생했다.
- 해당 메소드와 관련한 쿼리문을 Fetch Join을 통해 변경해 N+1 문제를 해소했다.


Lv2 - 3. QueryDSL

- getTodo() 기능을 QueryDSL으로 변경했다.
- (2025.01.20 추가) N+1문제 발생으로 fetchJoin을 추가했다.

Lv2 - 4. Spring Security

- 기존 Filter와 Argument Resolver를 사용하던 코드들을 Spring Security로 변경했다.
  
</details>

<details>
<summary>Lv3</summary>
Lv3 - 10. QueryDSL을 사용하여 검색 기능 만들기

- 일정 검색 기능(제목, 생성일 범위, 닉네임)을 추가했다.
- 해당 기능으로 일정을 검색하면 해당 일정의 제목, 담당자 수, 댓글 개수를 볼 수 있다.

Lv3 - 11. Transcation 심화

Lv3 - 12. AWS 활용 마스터

Lv3 - 13. 대용량 데이터 처리
</details>

<details>
  Lv4. Kotlin 적용하기

  Lv4 - 14. Entity 및 Repository CRUD를 Kotlin으로 리팩토링
</details>
