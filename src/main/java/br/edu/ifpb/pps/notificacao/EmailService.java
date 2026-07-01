package br.edu.ifpb.pps.notificacao;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.edu.ifpb.pps.apresentacao.ApresentacaoConsole;
import br.edu.ifpb.pps.apresentacao.ServicoApresentacao;

public class EmailService implements ServicoNotificacao {

    private final String remetente;
    private final String senha;
    private final boolean envioReal;
    private final ServicoApresentacao apresentacao;

    public EmailService() {
        this("", "", false, new ApresentacaoConsole());
    }

    public EmailService(ServicoApresentacao apresentacao) {
        this("", "", false, apresentacao);
    }

    public EmailService(String remetente, String senha) {
        this(remetente, senha, true, new ApresentacaoConsole());
    }

    public EmailService(String remetente, String senha, ServicoApresentacao apresentacao) {
        this(remetente, senha, true, apresentacao);
    }

    private EmailService(String remetente, String senha, boolean envioReal, ServicoApresentacao apresentacao) {
        this.remetente = remetente;
        this.senha = senha;
        this.envioReal = envioReal;
        this.apresentacao = apresentacao;
    }

    public static EmailService configurarPorAmbiente(ServicoApresentacao apresentacao) {
        String remetente = System.getenv("EMAIL_REMETENTE");
        String senha = System.getenv("EMAIL_SENHA");

        if (remetente == null || senha == null || remetente.isBlank() || senha.isBlank()) {
            return new EmailService(apresentacao);
        }

        return new EmailService(remetente, senha, apresentacao);
    }

    @Override
    public void enviar(String destinatario, String assunto, String mensagem) {
        if (!envioReal || remetente.isBlank() || senha.isBlank()) {
            exibirNoConsole(destinatario, assunto, mensagem);
            return;
        }

        try {
            enviarPorSmtp(destinatario, assunto, mensagem);
        } catch (MessagingException e) {
            apresentacao.exibir("Não foi possível enviar o e-mail (" + e.getMessage() + ").");
            exibirNoConsole(destinatario, assunto, mensagem);
        }
    }

    private void enviarPorSmtp(String destinatario, String assunto, String mensagem) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remetente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(assunto);
        message.setText(mensagem);

        Transport.send(message);
    }

    private void exibirNoConsole(String destinatario, String assunto, String mensagem) {
        String separador = "---------------------------------------------";
        apresentacao.exibir(separador
                + "\nPara: " + destinatario
                + "\nAssunto: " + assunto + "\n"
                + separador + "\n"
                + mensagem + "\n"
                + separador);
    }
}
