# vefforritun-IdleIsland
Lokaverkefni í Vefforritun

## Project Setup
- git clone https://github.com/thorgeir93/vefforritun-IdleIsland.git
- cd vefforritun-IdleIsland
- npm install
- gulp

## Database Connection
- Búa til database með PostgreSQL
- Opna CBConnector.js (./lib/DBConnector.js)
- Búa til local breytu 'DATABASE'
- 'var DATABASE = postgres://username:password@localhost/database'
- Svo þarf að comment'a um þessa breytu

## Git commands
- git add .
- git reset node_modules/
- git commit -m "Message"
- git pull origin master
- [laga conflict]
- git push origin master