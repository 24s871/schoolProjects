library ieee;
use ieee.std_logic_1164.all;

entity comparator_2 is
	port(a,b:in std_logic_vector (1 downto 0);
	enable:in std_logic;
	egal:out std_logic);
end entity;

architecture comportamental of comparator_2 is	

begin  
	process(a,b,enable)
	begin  
		if(enable<='1') then
			if(a=b) then
				egal<='1';
				else egal<='0';
			   end if; 
			   end if;
		   end process;
		   end architecture;
