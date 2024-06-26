# seatApp

## 使用している主な技術

### バックエンド

spring-boot

### フロントエンド

#### フレームワーク

React

#### ライブラリ

React Bootstrap<br>
axios<br>
Leaflet<br>
React Leaflet

## プロジェクトの概要

オフィスの座席にネームプレートを置いて
誰がどこに座っているか明確になるアプリです。

操作方法は
まずユーザー登録ボタンを押して自分の名前を入力してボタンを押すと名前が表示されます
表示された名前を押してから座席を押すと名前が表示されます。
座席から名前を消す時は名前をクリックしてください。
これが基本的な使い方です。

## 開発環境の構築方法

今回はIntelliJを使用した方法です（フロントエンドはVScodeでも可）

### バックエンド

ターミナルでpsqlを入力しpsqlを開く
CREATE DATABASE seatdb と入力しdbを作成

IntelliJで開く
src-main-resources-application.propertiesファイルを開く
spring.datasource.url=jdbc:postgresql://dpg-cpkjntsf7o1s73cq8cdg-a:5432/seatdbをコメントアウト
spring.datasource.url=jdbc:postgresql://localhost/seatdbを生かす

右側のゾウさんのアイコンからTasks-Application-bootRunを起動するとサーバーが起動します

### フロントエンド

ターミナルでcd frontendを入力し、続いて以下のコマンドを入力

npm install

npm run dev

表示されたLocalhostのリンクにアクセスし、ページが表示されたらOK

## デプロイ先

https://seatapp-1.onrender.com

