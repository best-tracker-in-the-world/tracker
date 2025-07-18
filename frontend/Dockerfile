FROM node:20-alpine

WORKDIR /usr/src/nuxt-app

RUN npm prune --production

COPY . .

RUN npm i && npm cache clean --force
RUN npm run build

EXPOSE 3000

CMD ["node", "./.output/server/index.mjs"]