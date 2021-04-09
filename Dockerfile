FROM openjdk:11
COPY target/*.jar /app/proposta.jar
WORKDIR /app
EXPOSE 8080
ENV DB_USERNAME=root
ENV DB_PASSWORD=root
ENV DB_HOST=mysql
ENV DB_PORT=3306
ENV CARD_URL=contas
ENV PROPOSAL_URL=analise
ENTRYPOINT java -jar proposta.jar