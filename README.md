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
- Dependencies
  - Spring Reactive Web
  - Lombok

生成された `build.gradle` は以下
```groovy
plugins {
    id 'org.springframework.boot' version '2.6.7'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '17'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'io.projectreactor:reactor-test'
}

tasks.named('test') {
    useJUnitPlatform()
}
```
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
@RequestMapping("publish")
public class WebFluxPublisherController {

  @GetMapping("greeting")
  Mono<String> greeting() {
    return Mono.just("Hello, WebFlux!");
  }

  @GetMapping("numberstream")
  Flux<Long> numberStream() {
    return Flux.interval(Duration.ofMillis(500)); // 0.5秒毎に数値を0からカウントアップして返す
  }

}
```

`Mono#just` は、指定された値を出力する Mono を生成する静的メソッド。</br>
`Flux#interval` は、指定時間間隔で 0 から増加させる Flux を生成する静的メソッド。

#### curl で API を叩いてみる
- mono
```shell
$ curl http://localhost:8080/publish/greeting
Hello, WebFlux!
```
- flux (Stream で受け取るには、ヘッダに `Accept: test/event-stream` を指定する必要がある)
```shell
$ curl -H "Accept: text/event-stream" http://localhost:8080/publish/numberstream  
data:0

data:1

data:2

data:3

data:4

^C          
```

## 参考サイト
[今こそ知りたいSpring Web（Spring Fest 2020講演資料）](https://www.slideshare.net/nttdata-tech/spring-fest-2020-spring-web-nttdata)
[マイクロサービス時代に活きるフレームワーク Spring WebFlux入門](https://news.mynavi.jp/techplus/series/_spring_webflux/)