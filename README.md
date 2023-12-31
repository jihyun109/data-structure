# data-structure
data structure

## 윈도우에서 추가하는 방법
### 1. git 설치하기
### 2. 유저(인증) 정보 세팅하기
```
git config user.name xxxx
git config user.email xxxx
git config credential.username xxxx
```
> 이것들은 .git/config 에 반영된다
### 3. add remote origin 지정하기 
```
git remote add origin git-url
```
OR
```
git clone git-url
```
> 그냥 쉽게 클론을 하고 , 학습한 코드들을 추가 해서 올리는게 편할수도 있습니다
### 4. add , commit , push(+ 최초 인증)
```
git add .
git commit -m "msg"
git push origin main
```

### 5. 부가적인 명령어들
```
git 
git branch : show list branch
git checkout <branch>
git checkout -b <branch> : create new one , and checkout to it
```
