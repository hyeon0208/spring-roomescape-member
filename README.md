# 미션 2 기능 요구 사항

- [테마 API 요청 파일 바로가기](api-test/theme-api.http) <br>
- [예약 시간 API 요청 파일 바로가기](api-test/reservationtime-api.http) <br>
- [예약 API 요청 파일 바로가기](api-test/reservation-api.http)

## 예외 처리

#### reservation (예약)
- [x] 예약자 명을 null, 빈 문자열, 공백으로 요청할 수 없다.
- [x] 예약자 명은 4글자까지 입력 가능하다.
- [x] 예약자 명은 한글로만 입력 가능하다.
- [x] 날짜 생성 시 날짜에 유효하지 않은 값을 요청할 수 없다.
- [x] 지나간 날짜로 예약할 수 없다.
- [x] 중복 예약은 불가능하다.
  - ex. 이미 4월 1일 10시에 예약이 되어있다면, 4월 1일 10시에 대한 예약을 생성할 수 없다.

#### theme (테마)
- [x] 테마 명을 null, 빈 문자열, 공백으로 요청할 수 없다.
- [x] 테마 명은 10글자까지 입력 가능하다.
- [x] 테마 명에 공백을 포함할 수 있고  한글, 영어, 숫자만 입력가능하다.
- [x] 중복된 테마명은 추가할 수 없다.
- [x] 설명을 null, 빈 문자열, 공백으로 요청할 수 없다.
- [x] 설명에 공백을 포함할 수 있고 한글, 숫자만 입력가능하다.
- [x] 설명은 50자까지 입력 가능하다.
- [x] 썸네일 주소를 null, 빈 문자열, 공백으로 요청할 수 없다.

#### reservationTime (예약시간)
- [x] 시작 시간을 null로 요청할 수 없다.
- [x] 특정 시간에 대한 예약이 존재하는데, 그 시간을 삭제 할 수 없다.
