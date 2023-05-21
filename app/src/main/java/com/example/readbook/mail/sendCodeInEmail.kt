package com.example.readbook.mail

import java.util.Properties
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

fun sendCodeInEmail(
    to: String
) {
    val prop = Properties()
    prop["mail.smtp.host"] = "smtp.yandex.ru"
    prop["mail.smtp.ssl.enable"] = true
    prop["mail.smtp.port"] = 465
    prop["mail.smtp.auth"] = true

    val session =  Session.getDefaultInstance(prop,
        object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(Credentials.EMAIL, Credentials.PASSWORD)
            }
        })

    val msg = MimeMessage(session)
    msg.setFrom(InternetAddress(Credentials.EMAIL))
    msg.setRecipients(Message.RecipientType.TO, to)
    msg.setSubject("Код для восстановления пароля")
    msg.setText(generateCode())

    Transport.send(msg)
}

fun generateCode(): String {
    var code = ""
    for(i in 1..6)
        code += (0..9).random().toString()
    Code.value = code
    return code
}