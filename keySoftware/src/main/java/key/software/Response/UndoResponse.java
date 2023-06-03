package key.software.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UndoResponse {
    private String message;

    public UndoResponse(String undoType)
    {
        this.message = undoType+ " Undo Successfully";
    }
}

