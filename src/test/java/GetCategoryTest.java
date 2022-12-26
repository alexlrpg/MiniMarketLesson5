import api.CategoryService;
import dto.GetCategoryResponce;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.RetrofitUtils;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetCategoryTest {

    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        Response <GetCategoryResponce> responce = categoryService.getCategory(1).execute();
        assertThat(responce.isSuccessful(), CoreMatchers.is(true));
        assertThat(responce.body().getId(), equalTo(1));
        assertThat(responce.body().getTitle(), equalTo("Food"));
        responce.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Food")));
    }

    @SneakyThrows
    @Test
    void getCategoryByIdNegativeTest() {
        Response <GetCategoryResponce> responce = categoryService.getCategory(3).execute();
        assertThat(responce.isSuccessful(), CoreMatchers.is(false));
        assertThat(responce.code(), equalTo(404));
    }
}
