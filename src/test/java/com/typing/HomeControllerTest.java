package com.typing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import com.typing.login.domain.service.UserService;

import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// モックとして使用するbeanにはこのアノテーションをつける。
	@MockBean
	private UserService service;

	@Test
	@WithMockUser // ログイン後にしか表示できない画面のテストができるようになるアノテーション。
	public void ユーザーリスト画面のユーザー件数のテスト() throws Exception {
		// UserServiceのcountメソッドの戻り値を10に設定
		when(service.count()).thenReturn(10);

		// ユーザーリストの画面チェック
		mockMvc.perform(get("/userList")).andExpect((status().isOk()))
				.andExpect(content().string(containsString("合計: 10件")));
	}

}
