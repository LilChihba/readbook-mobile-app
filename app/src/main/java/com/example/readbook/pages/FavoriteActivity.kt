import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.ui.theme.ButtonBook
import com.example.readbook.ui.theme.Gray
import com.example.readbook.ui.theme.Milk

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun FavoritePage() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
        .padding(start = 25.dp, top = 30.dp, end = 25.dp)
    ) {
        Text(
            text = "Мои книги",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = Gray),
            modifier = Modifier
                .fillMaxWidth()
                .height(610.dp)
        ) {
            LazyColumn() {
                items(count = 50) {
                    LazyRow() {
                        items(count = 3) {
                            ButtonBook()
                        }
                    }
                }
            }
        }
    }
}