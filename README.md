# Spring WebFlux 手習い
## 1. 環境構築
### Spring Initializr
下記設定で、Spring プロジェクトを作成
- Project
  - Grade Project
  - Language
    - Java
  - Spring Boot
    - 2.6.7
  - Project Metadata
    - Group: com.example
    - Artifact: springWebFlux
    - Name: springWebFlux
    - Package name: com.example.springwebflux
    - Packaging: jar
    - Java: 17
### jEnv
Java 環境を Jenv で指定
```commandline
jenv local 17.0.3
```
## 2. プロジェクトの初期状態を Github に登録
```commandline
git init
git commit -m "first commit"
git remote add origin git@github.com:xxx.git
git push -u origin master
```

## 3. mono / flux を返す RestController を作ってみる
```java
@RestController
public class WebFluxAnnotationController {

    @GetMapping("mono")
    Mono<String> mono() {
        return Mono.just("Hello, WebFlux!");
    }

    @GetMapping("flux")
    Flux<String> flux() {
        return Flux.interval(Duration.ofSeconds(1)).map(aLong -> LocalDateTime.now().toString());
    }

}
```
#### curl で API を叩いてみる
- mono
```shell
$ curl http://localhost:8080/mono
Hello, WebFlux!
```
- flux (Stream で受け取るには、`test/event-stream` で受け取る必要がある)
```shell
$ curl -H "accept: text/event-stream" http://localhost:8080/flux  
data:2022-05-11T18:45:37.264472

data:2022-05-11T18:45:38.264507

data:2022-05-11T18:45:39.265814

data:2022-05-11T18:45:40.264431

data:2022-05-11T18:45:41.264413

data:2022-05-11T18:45:42.264400

^C
```
