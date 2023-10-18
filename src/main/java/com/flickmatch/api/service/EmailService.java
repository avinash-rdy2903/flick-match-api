package com.flickmatch.api.service;

import com.flickmatch.api.model.User;
import com.flickmatch.api.pojoClass.AuthenticationResponse;
import com.flickmatch.api.repository.OtpRepository;
import com.flickmatch.api.repository.UserRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Environment env;

    private final UserRepository userRepository;

    private final OtpRepository otpRepository;

    private final JwtService jwtService;

    private final JavaMailSender javaMailSender;



    public AuthenticationResponse generateOtp(String email) throws Exception{
        String senderEmail = env.getProperty("spring.mail.username");
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("SystemError")
        );
        String token = jwtService.generateToken(user);
        MimeMessage message  = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(MimeMessage.RecipientType.TO, "recipient@example.com");
        message.setSubject("FlickMatch - Reset Password Otp");
        String htmlContent = "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>SVMUKl</title>\n" +
                "    <style>\n" +
                "        .footer-link li a:hover {\n" +
                "            color: #227df0 !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"margin:0; background:#f3f3f3;\">\n" +
                "    <!-- wrapper table -->\n" +
                "    <table width=\"100%\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"margin:0; background:#f3f3f3;\"\n" +
                "        bgcolor=\"#f3f3f3\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td height=\"30\" style=\" font-size:0; line-height:0;\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"center\" valign=\"top\" style=\"font-size:0; line-height:0; background:#f3f3f3;\"\n" +
                "                    bgcolor=\"#f3f3f3\">\n" +
                "                    <!-- Main Table -->\n" +
                "                    <table width=\"601\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                        style=\" width:601px; margin:0 auto; background:#fff;\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td style=\"padding:15px 30px;\">\n" +
                "                                    <img src=\"https://static-cdn2.mycardplace.com/componentcontentdelivery/cstatic/907a5aed-9955-4998-bac0-5227405689e7/zolve32-logo.png\" width=\"100\" alt=\"Zolve\">\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"border-bottom:2px solid #f3f3f3; font-size:0; line-height:0;\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td height=\"15\" style=\" font-size:0; line-height:0;\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <!--*************-->\n" +
                "                                <td style=\"padding: 0 38px;\"\"> <!--*************-->\n" +
                "\t\t\t\t\t<table width=\" 100%\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%;\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td\n" +
                "                                    style=\" font-family:Arial, Helvetica, sans-serif; color:#3e3e3e; font-size:14px; line-height:26px; font-weight: 700;\">\n" +
                "                                    Dear Customer, </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td height=\"10\" style=\"font-size:0; line-height:0;\"> </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td\n" +
                "                                    style=\" font-family:Arial, Helvetica, sans-serif; color:#3e3e3e; font-size:14px; line-height:26px; \">\n" +
                "                                    <p>723461 is your OTP to continue using Zolve Credit/Debit for account number\n" +
                "                                        3265. Do not share it with anyone.</p>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <!-- <td style=\"font-family:Arial, Helvetica, sans-serif; color:#333; font-size:14px; line-height:24px;\">\n" +
                "\t\t\t\t\t\t\t\t<div style=\"padding:10px 10px; background:#eef5e9;  border: solid 1px #ddd;font-weight: normal;\">\n" +
                "\t\t\t\t\t\t\t\t\t <strong>One Time Password : 723461</strong>\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t</td> -->\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td height=\"15\" style=\" font-size:0; line-height:0;\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\" font-family:Arial, Helvetica, sans-serif; color:#3e3e3e; font-size:14px; line-height:26px;\">\n" +
                "                    Please Note: <br>\n" +
                "                    One Time Password has also been sent to your registered mobile number. Please\n" +
                "                    DO NOT share your OTP with anyone or respond to any email requesting such\n" +
                "                    information.\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td height=\"15\" style=\" font-size:0; line-height:0;\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\" font-family:Arial, Helvetica, sans-serif; color:#3e3e3e; font-size:14px; line-height:26px;\">\n" +
                "                    To report fraud, please contact the Zolve support on the in-app chat or on\n" +
                "                    <a href=\"mailto:hello@zolve.com\">hello@zolve.com</a>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td height=\"15\" style=\" font-size:0; line-height:0;\"></td>\n" +
                "            </tr>\n" +
                "            <!-- Ending Greetings -->\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#ffffff\" valign=\"top\" style=\"padding: 0 38px;\"\">\n" +
                "                    <table bgcolor=\" #ffffff\" width=\"100%\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "            style=\"width:100%;\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" valign=\"top\"\n" +
                "                        style=\"font-family: Calibri, sans-serif; font-size: 16px; line-height: 1.5; color: #000000;\">\n" +
                "                        Team Zolve\n" +
                "</td>\n" +
                "                </tr>\n" +
                "                <!-- <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\" height=\"10\">\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" valign=\"top\"\n" +
                "                        style=\"font-family: Calibri, sans-serif; font-size: 16px; line-height: 1.5; color: #000000;\">\n" +
                "                        Customer Services</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\" height=\"2\">\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" valign=\"top\"\n" +
                "                        style=\"font-family: Calibri, sans-serif; font-size: 16px; line-height: 1.5; color: #000000;\">\n" +
                "                        Phone: <a href=\"tel:+4402038933375\" target=\"_blank\"\n" +
                "                            style=\"outline: none; text-decoration: none; color: #000000; font-family: Calibri, sans-serif; font-size: 16px; line-height: 1.5;\"\n" +
                "                            title=\"Phone\">+ 44 (0) 20 38933375</a></td>\n" +
                "                </tr> -->\n" +
                "                <!-- <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\" height=\"2\">\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" valign=\"top\"\n" +
                "                        style=\"font-family: Calibri, sans-serif; font-size: 16px; line-height: 1.5; color: #000000;\">\n" +
                "                        Email: <a href=\"mailto:test@test.com\" target=\"_blank\"\n" +
                "                            style=\"outline: none; text-decoration: none; color: #000000; font-family: Calibri, sans-serif; font-size: 16px; line-height: 1.5;\"\n" +
                "                            title=\"Email\">test@test.com</a></td>\n" +
                "                </tr> -->\n" +
                "            </tbody>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#ffffff\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\" height=\"15\"></td>\n" +
                "    </tr>\n" +
                "    <!-- <tr>\n" +
                "        <td bgcolor=\"#f3f3f3\"\n" +
                "            style=\"font-size: 0;line-height: 0;border-collapse: collapse; padding: 0; background-color: #f3f3f3;\">\n" +
                "            <hr\n" +
                "                style=\"margin: 0; padding: 0; background-color: #f3f3f3; color: #f3f3f3; border: none; height: 1.5px;\" />\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#ffffff\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\" height=\"15\"></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#ffffff\" valign=\"top\"\n" +
                "            style=\"font-family: Calibri, sans-serif; font-weight:normal; margin:0 0 10px; font-size: 16px; color: #000000; padding: 0 38px; font-style:italic;\">\n" +
                "            This is an automated email. Please do not reply.</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#ffffff\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\" height=\"25\"></td>\n" +
                "    </tr> -->\n" +
                "    </tbody>\n" +
                "    </table>\n" +
                "    <!-- end of Main Table -->\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td height=\"25\" valign=\"top\" style=\"height:25px; font-size:0; line-height:0;\"></td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "    </table>\n" +
                "    <!-- end of wrapper table -->\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        message.setContent(htmlContent,"text/html; charset=utf-8");

        javaMailSender.send(message);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

}
