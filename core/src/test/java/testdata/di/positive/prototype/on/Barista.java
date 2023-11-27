package testdata.di.positive.prototype.on;

import com.bobocode.bring.core.annotation.Scope;
import com.bobocode.bring.core.annotation.Service;
import com.bobocode.bring.core.domain.BeanScope;
import com.bobocode.bring.core.domain.ProxyMode;
import lombok.Getter;

import java.util.UUID;

@Getter
@Service
@Scope(name = BeanScope.PROTOTYPE, proxyMode = ProxyMode.ON)
public class Barista {
    
    private final UUID uuid = UUID.randomUUID();
    
}
