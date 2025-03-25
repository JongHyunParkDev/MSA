# MSA
MSA E-commerce 구현


## 사용 툴 및 아키텍쳐
- Docker
  - Docker Swarm 사용 + shell script - (Auto Scaling)
  - OR
  - Docker -> K8S 사용 
- SPRING CLOUD EUREKA (DISCOVERY SERVICE)
- SPRING CLOUD API-GATEWAY
  - 인가 
  - Load Balnencing (Round Robin)
- MONGODB
  - JSON 구조 데이터 사용
- POSTGRESQL
  - 일반적인 DB 사용됨 (MSA 마다 POSTGRESQL 설치) (SERVICE 대 수, instance 별로 생성 X)
- REDIS (인증, 인가) or JWT token
  - SESSION 관리
- KAFKA
  -  SAGA 패턴 적용
  -  Service 에 event 연동
- SPRING BOOT, JAVA, JPA (Hibernate), QueryDSL, Actuator
  - MEMBER SERVICE
  - ORDER SERVICE
  - PRODUCT SERVICE
  - PAY SERVICE (실제 결재는 안 됨으로 DB Data 로 한다)
- Node.js
  -  Front Web Server (Build 된 html, js, css)
- Front
  - Quasar, Pinia, Vue Router 
- RestAPI, STOMP (Web Socket), SSE
  - 필요시 사용
- 추 후 도입
  - Promethues (매트릭 수집)
  - Grafana (서버 자원 모니터링) 
  - PinPoint (분산 서비스 자원 모니터링)
  - Jenkins (CI/CD)
    - Service instance 무중단 배포
   
---
## 구상 중인 아키텍쳐 (변경될 수 있음)
  ![image](https://github.com/user-attachments/assets/3c7b12d9-2acd-47f8-bb6d-911c3a4c6ab0)
