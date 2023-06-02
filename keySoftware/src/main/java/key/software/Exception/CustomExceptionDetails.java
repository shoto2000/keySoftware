package key.software.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionDetails {
	
	private LocalDateTime localDateTime;
	private String message;
	private String description;

}