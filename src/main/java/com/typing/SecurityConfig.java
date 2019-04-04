package com.typing;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// セキュリティ設定用クラス
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // パスワードエンコーダーのBean定義
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // データソース
    @Autowired
    private DataSource dataSource;

    // ユーザーIDとパスワードを取得するSQL
    private static final String USER_SQL = "select" + " user_id," + " password," + " true" // 使用可否
            + " from" + " m_user" + " where" + " user_id = ?";
    // ユーザーのロールを取得するSQL
    private static final String ROLE_SQL = "select" + " user_id," + " role" + " from" + " m_user" + " where"
            + " user_id = ?";

    @Override
    public void configure(WebSecurity web) throws Exception {

        // 静的リソースへのアクセスにはセキュリティを適用しない
        web.ignoring().antMatchers("/webjars**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 直リンク禁止とログイン不要ページの設定
        http.authorizeRequests().antMatchers("/webjars/**").permitAll() // webjarsへアクセス許可
                .antMatchers("/css/**").permitAll() // cssへアクセス許可
                .antMatchers("/login").permitAll() // ログインページは直リンクOK
                .antMatchers("/signup").permitAll() // ユーザー登録画面は直リンクOK
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN") // URLの認可
                .anyRequest().authenticated(); // それ以外は直リンクNG

        // ログイン処理
        http.formLogin().loginProcessingUrl("/login") // ログイン処理のパス
                .loginPage("/login") // ログインページの指定
                .failureUrl("/login") // ログイン失敗時の遷移先
                .usernameParameter("userId") // ログインページのユーザーID
                .passwordParameter("password") // ログインページのパスワード
                .defaultSuccessUrl("/home", true); // ログイン成功後の遷移先

        // ログアウト処理(ログアウトすることでセッション破壊)
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        // CSRF対策
        // http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // ログイン処理時のユーザー情報をDBから取得
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(USER_SQL)
                .authoritiesByUsernameQuery(ROLE_SQL).passwordEncoder(passwordEncoder());
    }
}