package key.software.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DeleteResponse {

    private String message;

    public DeleteResponse(String deleteType)
    {
        this.message = deleteType+ " Deleted Successfully";
    }

}